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
    if (!body.username || !body.email || !body.password || !body.phoneNumber || !body.firstname || !body.lastname || !body.birthDate) {
      console.log(body);
      return res.status(400).send("All fields must be filled");
    }

    const userExistence = await User.findOne({ username: body.username });
    if (userExistence) {
      return res.status(400).send("Username already exists");
    }

    const emailExistence = await User.findOne({ email: body.email });
    if (emailExistence) {
      return res.status(400).send("Email already exists");
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isValidEmail = emailRegex.test(body.email);
    if (!isValidEmail) {
      return res.status(400).send("Email is not valid");
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
    res.status(500).send("Internal server error");
  }
});

router.get("/getUsers", checkAccessToken, async (req, res) => {
  try {
    const response = await User.find();
    res.send(response);
  } catch (error) {
    console.error("Error fetching users:", error);
    res.status(500).send("Internal server error");
  }
});

//Sign-in
router.post("/auth/signin", async (req, res) => {
  try {
    const { body } = req;
    if (!body.username || !body.password) {
      return res.status(400).send("All fields must be filled");
    }

    const user = await User.findOne({ username: body.username });
    if (!user) {
      return res.status(401).send("Invalid credentials");
    }

    const isPasswordValid = await bcrypt.compare(body.password, user.password);
    if (!isPasswordValid) {
      return res.status(401).send("Invalid credentials");
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
    await new Refresh({ refreshToken: refreshToken, username: user._id }).save();

    res.status(200).json({
      accessToken,
      refreshToken,
      user: {
        id: user._id,
        username: user.username,
        email: user.email,
        phoneNumber: user.phoneNumber,
        profilePhoto: user.profilePhoto,
        licensePhoto: user.licensePhoto,
        firstName: user.firstName,
        lastName: user.lastName,
      },
    });
  } catch (error) {
    console.error("Signin error:", error);
    res.status(500).send("Internal server error");
  }
});

//RefreshToken
router.post("/auth/refresh", async (req, res) => {
  try {
    console.log("shiiii in the refresh route")
    const { refreshToken } = req.body;
    console.log(refreshToken)
    if (!refreshToken) {
      console.log("shiiii no refreshToken")
      return res.status(404).send("Refresh token not found");
    }

    const storedToken = await Refresh.findOne({ refreshToken: refreshToken });
    console.log("storedToken:", storedToken)
    if (!storedToken) {
      console.log("shiiii no stored token")
      return res.status(401).send("Invalid refresh token");
    }

    jwt.verify(refreshToken, refreshToken_Secret, async (err, decoded) => {
      console.log("shiiii in the verify function")
      if (err) {
        console.log("shiiii in the verify errr")
        console.error(err);
        return res.status(401).send("Invalid refresh token");
      }

      if (!decoded) {
        console.log("shiii no decoded")
        return res.status(401).send("Invalid refresh token");
      }

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
    });
  } catch (error) {
    console.error("Refresh token error:", error);
    res.status(500).send("Internal server error");
  }
});

//check if accessToken is present
router.get("/checkAccessToken",checkAccessToken,(req,res)=>{
  try{
    res.status(200).send(req.user)
  }
  catch(e){
    res.status(400).send("Some error happened: e.message")
    console.log(e)
  }
})

// check if the user's Legitmacy
router.get("/api/checkLegitimacy",checkAccessToken,async (req, res) => {
  const { username } = req.query;

  try {
    const user = User.find({
      username: username,
    });
    if (!user) {
      return res.status(404).send("User can't be found");
    }
    if (!user.licensePhoto || !user.profilePhoto) {
      return res.status(400).send("license and profile photo aren't uploaded");
    } else {
      return res.status(200).send(user);
    }
  } catch (e) {
    res.status(400).send(e.message);
    console.log(e);
  }
});

export default router;