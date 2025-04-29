import { Router } from "express";
import jwt from "jsonwebtoken";
import bcrypt from "bcrypt";
import User from "../models/users.js";
import { config } from "dotenv";
import Refresh from "../models/refresh.js";
import checkAccessToken from "../middleware/checkAccessToken.js";

const router = Router();
config();

const accessToken_Secret = process.env.ACCESS_TOKEN;
const refreshToken_Secret = process.env.REFRESH_TOKEN;

//Sign-up
router.post("/auth/signup", async (req, res) => {
  try {
    const { body } = req;
    if (!body.username || !body.email || !body.password ||!body.phoneNumber ||!body.firstname ||!body.lastname ||!body.birthDate) {
      console.log(body)
      return res.status(400).json({ error: "All fields must be filled" });
    }

    const userExistence = await User.findOne({ username: body.username });
    if (userExistence) {
      return res.status(400).json({ error: "Username already exists" });
    }

    const emailExistence = await User.findOne({ email: body.email });
    if (emailExistence) {
      return res.status(400).json({ error: "Email already exists" });
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isValidEmail = emailRegex.test(body.email);
    if (!isValidEmail) {
      return res.status(400).json({ error: "Email is not valid" });
    }

    const salt = 10;
    const hashedPassword = await bcrypt.hash(body.password, salt);

    const newUser = new User({
      username: body.username,
      password: hashedPassword,
      email: body.email,
      firstName: body.firstname,
      lastName: body.lastname,
      birthDate: body.birthDate,
      phoneNumber: body.phoneNumber,
    });

    const savedUser = await newUser.save();
    res.status(201).json(savedUser);
  } catch (error) {
    console.error("Signup error:", error);
    res.status(500).json({ error: "Internal server error" });
  }
});

router.get("/getUsers",checkAccessToken,async (req, res) => {
  const response = User.findAll();
  res.send(response);
});

//Sign-in
router.post("/auth/signin", async (req, res) => {
  try {
    const { body } = req;
    if (!body.username || !body.password) {
      return res.status(400).json({ error: "All fields must be filled" });
    }

    const user = await User.findOne({ username: body.username });
    if (!user) {
      return res.status(401).send("Invalid credentials");
    }

    const isPasswordValid = await bcrypt.compare(body.password, user.password);
    if (!isPasswordValid) {
      return res.status(401).json({ error: "Invalid credentials" });
    }

    const accessToken = jwt.sign(
      { userId: user._id, username: user.username },
      accessToken_Secret,
      { expiresIn: "15m" }
    );

    const refreshToken = jwt.sign(
      { userId: user._id, username: user.username },
      refreshToken_Secret,
      { expiresIn: "3d" }
    );

    // Save refresh token
    await new Refresh({ refreshToken: refreshToken, username:user._id }).save();

    res.status(200).json({
      accessToken,
      refreshToken,
      user: {
        id: user._id,
        username: user.username,
        email: user.email,
        phoneNumber:user.phoneNumber,
        profilePhoto:user.profilePhoto,
        licensePhoto:user.licensePhoto,
        firstName:user.firstName,
        lastName:user.lastName
      },
    });
  } catch (error) {
    console.error("Signin error:", error);
    res.status(500).json({ error: "Internal server error" });
  }
});

//RefreshToken
router.post("/auth/refresh", async (req, res) => {
  try {
    const { refreshToken } = req.body;
    if (!refreshToken) {
      return res.status(404).json({ error: "Refresh token Not found" });
    }

    const storedToken = await Refresh.findOne({ token: refreshToken });
    if (!storedToken) {
      return res.status(401).json({ error: "Invalid refresh token" });
    }

    jwt.verify(refreshToken, refreshToken_Secret,(err,decoded)=>{
      if(err){
        return res.status(401).json({ error: "Invalid refresh token" });

      }
      if (!decoded) {
        return res.status(401).json({ error: "Invalid refresh token" });
      }
    })
    

    const accessToken = jwt.sign(
      { userId: decoded.userId, username: decoded.username },
      accessToken_Secret,
      { expiresIn: "15m" }
    );

    const newRefreshToken = jwt.sign(
      { userId: decoded.userId, username: decoded.username },
      refreshToken_Secret,
      { expiresIn: "3d" }
    );

    // Update stored refresh token
    await Refresh.findOneAndUpdate(
      { token: refreshToken },
      { token: newRefreshToken }
    );

    res.status(200).json({
      accessToken,
      refreshToken: newRefreshToken,
    });
  } catch (error) {
    console.error("Refresh token error:", error);
    res.status(500).json({ error: "Internal server error" });
  }
});



//check if the user's Legitmacy
// router.get("api/checkLegitimacy", async (req, res) => {
//   const { username } = req.query;

//   try {
//     const user = User.find({
//       username: username,
//     });
//     if (!user) {
//       return res.status(404).send("User can't be found");
//     }
//     if (!user.licensePhoto || !user.profilePhoto) {
//       return res.status(200).send(false);
//     } else {
//       return res.status(200).send(true);
//     }
//   } catch (e) {
//     res.status(400).send(e.message);
//     console.log(e);
//   }
// });

export default router;
