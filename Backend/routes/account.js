import { Router } from "express";
import jwt from "jsonwebtoken";
import bcrypt from "bcrypt";
import User from "../models/users.js";
import { config } from "dotenv";
import Refresh from "../models/refresh.js";
import checkAccessToken from "../middleware/checkAccessToken.js";
import { upload } from "../middleware/multer.js";
import profilePhoto from "../models/profilePhotos.js";
import licensePhoto from "../models/licensePhotos.js";
import verifyOwnership from "../middleware/verifyOwnership.js";
import Booking from "../models/bookings.js";
const router = Router();
config();

const accessToken_Secret = process.env.ACCESS_TOKEN;
const refreshToken_Secret = process.env.REFRESH_TOKEN;

//Sign-up
router.post("/auth/signup", async (req, res) => {
  try {
    const { body } = req;
    if (
      !body.username ||
      !body.email ||
      !body.password ||
      !body.phoneNumber ||
      !body.firstname ||
      !body.lastname ||
      !body.birthDate
    ) {
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

// router.get("/users", checkAccessToken, async (req, res) => {
//   try {
//     const usersArray = [];
//     const response = await User.find();

//     response.forEach(async (respons) => {
//       if (
//         respons.profilePhoto !== null ||
//         respons.profilePhoto !== "" ||
//         respons.profilePhoto !== undefined
//       ) {
//         const profile = respons.profilePhoto;
//         const PP = await profilePhoto.findOne({ id: profile });
//         if (!PP) {
//           respons.profilePhoto = "";
//         } else {
//           const PPurl = PP.url;
//           respons.profilePhoto = PPurl;
//         }
//       }
//       if (
//         respons.licensePhoto !== null &&
//         respons.licensePhoto !== "" &&
//         respons.licensePhoto !== undefined
//       ) {
//         const license = respons.licensePhoto;
//         const licenseDoc = await licensePhoto.findOne({ id: license });

//         if (!licenseDoc) {
//           respons.licensePhoto = "";
//         } else {
//           const licenseUrl = licenseDoc.url;
//           respons.licensePhoto = licenseUrl;
//         }
//       }
//       usersArray.push(respons);
//     });
//     if (!usersArray) {
//       return res.status(404).send("No Users found");
//     }

//     res.send(usersArray);
//   } catch (error) {
//     console.error("Error fetching users:", error);
//     res.status(500).send("Internal server error");
//   }
// });

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
    req.user = user;
    console.log(req.user);
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

    console.log(
      "User.profile and all of that " + user.licensePhoto + user.profilePhoto
    );
    // Save refresh token
    await new Refresh({
      refreshToken: refreshToken,
      username: user._id,
    }).save();
    const PP = await profilePhoto.findOne({ _id: user.profilePhoto });
    const LP = await licensePhoto.findOne({ _id: user.licensePhoto });
    console.log("PP and Lp" + PP, LP);
    const placeHolder = PP ? PP.url : null;
    const otherPlaceHolder = LP ? LP.url : null;
    res.status(200).json({
      accessToken,
      refreshToken,
      user: {
        id: user._id,
        username: user.username,
        email: user.email,
        phoneNumber: user.phoneNumber,
        profilePhoto: placeHolder,
        licensePhoto: otherPlaceHolder,
        firstName: user.firstName,
        lastName: user.lastName,
        role: user.role,
      },
    });
    console.log({
      accessToken,
      refreshToken,
      user: {
        id: user._id,
        username: user.username,
        email: user.email,
        phoneNumber: user.phoneNumber,
        profilePhoto: placeHolder,
        licensePhoto: otherPlaceHolder,
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
    console.log("shiiii in the refresh route");
    const { refreshToken } = req.body;
    console.log(refreshToken);
    if (!refreshToken) {
      console.log("shiiii no refreshToken");
      return res.status(404).send("Refresh token not found");
    }

    const storedToken = await Refresh.findOne({ refreshToken: refreshToken });
    console.log("storedToken:", storedToken);
    if (!storedToken) {
      console.log("shiiii no stored token");
      return res.status(401).send("Invalid refresh token");
    }

    jwt.verify(refreshToken, refreshToken_Secret, async (err, decoded) => {
      console.log("shiiii in the verify function");
      if (err) {
        console.log("shiiii in the verify errr");
        console.error(err);
        return res.status(401).send("Invalid refresh token");
      }

      if (!decoded) {
        console.log("shiii no decoded");
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
router.get("/checkAccessToken", checkAccessToken, (req, res) => {
  try {
    res.status(200).send(req.user);
  } catch (e) {
    res.status(400).send("Some error happened: e.message");
    console.log(e);
  }
});

// check if the user's Legitmacy
router.get("/api/checkLegitimacy", checkAccessToken, async (req, res) => {
  console.log("In the checkLegitmacy function");
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

router.post("/profileUpload", upload.single("image"), async (req, res) => {
  console.log("in the profileUpload");
  try {
    if (!req.file) {
      return res.status(400).json({ error: "No file uploaded" });
    }
    const imageUrl = `http://${req.get("host")}/uploads/${req.file.filename}`;
    // Save to MongoDB
    const newImage = new profilePhoto({
      filename: req.file.filename,
      path: req.file.path,
      size: req.file.size,
      url: imageUrl,
    });
    await newImage.save();

    res
      .status(200)
      .json({ url: imageUrl, message: "Upload successful", id: newImage._id });
    console.log({
      url: imageUrl,
      message: "Upload successful",
      id: newImage._id,
    });
  } catch (err) {
    res.status(500).send("Server error");
    console.log(err);
  }
});

router.post("/licenseUpload", upload.single("image"), async (req, res) => {
  console.log("in the licenseUpload");
  try {
    if (!req.file) {
      return res.status(400).json({ error: "No file uploaded" });
    }

    const imageUrl = `http://${req.get("host")}/uploads/${req.file.filename}`;

    // Save to MongoDB
    const newImage = new licensePhoto({
      filename: req.file.filename,
      path: req.file.path,
      size: req.file.size,
      url: imageUrl,
    });
    await newImage.save();

    // Return public URL
    res
      .status(200)
      .json({ url: imageUrl, message: "Upload successful", id: newImage._id });
  } catch (err) {
    res.status(500).send("Server error");
    console.log(err);
  }
});

router.patch("/auth/updateAccount/:id", async (req, res) => {
  const { id } = req.params;
  const updates = req.body;

  console.log("updates.profilePhoto" + updates.profilePhoto);
  console.log("updates.licensePhoto" + updates.licensePhoto);

  try {
    if (updates.profilePhoto) {
      const PP = await profilePhoto.findOne({ _id: updates.profilePhoto });
      if (PP) {
        updates.profilePhoto = PP._id;
      } else delete updates.profilePhoto;
    }

    if (updates.licensePhoto) {
      const LP = await licensePhoto.findOne({ _id: updates.licensePhoto });
      if (LP) {
        updates.licensePhoto = LP._id;
      } else delete updates.licensePhoto;
    }
    console.log("UPDATES", updates, updates.profilePhoto, updates.licensePhoto);

    console.log("Attempting to update account with ID:", id);
    console.log("Update data:", updates);

    const updatedUser = await User.findByIdAndUpdate(id, updates, {
      new: true,
      runValidators: true,
    });

    if (!updatedUser) {
      console.log("User not found with ID:", id);
      return res.status(404).send("User not found");
    }
    const pp = await profilePhoto.findOne({ _id: updatedUser.profilePhoto });
    const lp = await licensePhoto.findOne({ _id: updatedUser.licensePhoto });
    console.log("pp and lp", pp.url, lp.url);

    console.log("Successfully updated user:", {
      id: updatedUser._id,
      username: updatedUser.username,
      email: updatedUser.email,
      phoneNumber: updatedUser.phoneNumber,
      profilePhoto: pp ? pp.url : updatedUser.profilePhoto,
      licensePhoto: lp ? lp.url : updatedUser.licensePhoto,
    });

    res.status(200).json({
      message: "Update successful",
      user: {
        id: updatedUser._id,
        username: updatedUser.username,
        email: updatedUser.email,
        phoneNumber: updatedUser.phoneNumber,
        profilePhoto: pp ? pp.url : updatedUser.profilePhoto,
        licensePhoto: lp ? lp.url : updatedUser.licensePhoto,
      },
    });
  } catch (e) {
    console.error("Update error:", e);
    res.status(400).send(e.message);
  }
});

router.get("/total_users", async (req, res) => {
  try {
    const users = await User.findAll();
    if (!users || users.length === 0) {
      res.status(404).send("No User found!");
    } else {
      res.status(200).send(users.length);
    }
  } catch (e) {
    res.status(404).send("Some user error");
  }
});

router.get("bookingHistory", async (req, res) => {
  const id = req.user._id || req.user.id;
  const myAccount = await User.findOne({ _id: id });
  if (myAccount == null || booking) {
    return res.status(404).send("Not found!");
  }
  const historyArray = myAccount.history;
  if (!historyArray) {
    return res.status(404).send("nothing here");
  }
  res.status(200).send(historyArray);
});

// router.get("bookingHistory",async(req,res)=>{
//   const id=req.query.id
//   const myAccount=await User.findOne({_id:id})
//   if(myAccount==null||booking){
//     return res.status(404).send("Not found!")
//   }
//   const historyArray=myAccount.history
//   if(!historyArray){
//     return res.status(404).send("nothing here")
//   }
//   res.status(200).send(historyArray)

// })

router.get("user/:id", async (req, res) => {
  const id = req.params.id;
  const user = User.findOne({ id: id });
  try {
    if (!user) {
      return res.status(404).send("User not found");
    }
    const profile = respons.profilePhoto;
    const PP = await profilePhoto.findOne({ id: profile });
    if (!PP) {
      user.profilePhoto = "";
    } else {
      const PPurl = PP.url;
      user.profilePhoto = PPurl;
    }
    const license = respons.licensePhoto;
    const licenseDoc = await licensePhoto.findOne({ id: license });

    if (!licenseDoc) {
      user.licensePhoto = "";
    } else {
      const licenseUrl = licenseDoc.url;
      user.licensePhoto = licenseUrl;
    }
    res.send(user).status(200);
  } catch (e) {
    console.log(e);
    res.send(e.message).status(404);
  }
});

router.get("/history", async (req, res) => {
  const id = req.params.id;
  try {
    const final = await User.aggregate([
      { $match: { _id: ObjectId(id) } },
      { $unwind: "$history" },
      {
        $lookup: {
          from: "bookings",
          localField: "history.bookingId",
          foreignField: "_id",
          as: "booking",
        },
      },
      { $unwind: "$booking" },
      {
        $lookup: {
          from: "cars",
          localField: "booking.carId",
          foreignField: "_id",
          as: "car",
        },
      },
      { $unwind: "$car" },
      {
        $lookup: {
          from: "carImages",
          localField: "car.imageId",
          foreignField: "_id",
          as: "carImage",
        },
      },
      { $unwind: { path: "$carImage", preserveNullAndEmptyArrays: true } },
      {
        $project: {
          _id: 0,
          booking: 1,
          carImage: 1,
        },
      },
    ]);
    if (!final) {
      return res.status(404).send("Booking Not found");
    }
    res.status(200).send(final);
  } catch (e) {
    res.status(400).send(e.message);
    console.log(e);
  }

  res.status(200).send(history);
});

router.get("/users", async (req, res) => {
  try {
    const final = await User.aggregate([
      { $match: { role: "user" } },
      {
        $lookup: {
          from: "profilephotos",
          localField: "profilePhoto",
          foreignField: "_id",
          as: "PP",
        },
      },
      {
        $lookup: {
          from: "licensephotos",
          localField: "licensePhoto",
          foreignField: "_id",
          as: "LP",
        },
      },
      { $unwind: { path: "$PP", preserveNullAndEmptyArrays: true } },
      { $unwind: { path: "$LP", preserveNullAndEmptyArrays: true } },
    ]);
    if (!final) {
      return res.status(404).send("Not found");
    }
    res.status(200).send(final);
    console.log(final);
  } catch (e) {
    res.send(e).status(400);
  }
});

router.delete("/user/:id", async (req, res) => {
  const id = req.params.id;
  try {
    const deletedUser = await User.findByIdAndDelete(id);
    if (!deletedUser) {
      return res.status(404).send("User not found!");
    }
    res.status(200).send("User Deleted");
  } catch (err) {
    console.error(err);
    res.status(400).send(err.message);
  }
});

export default router;
