import { Router } from "express";
import jwt from "jsonwebtokens";
import bcrypt from "bcrypt";
import User from "../models/users.js";
import { config } from "dotenv";
import Refresh from "../models/refresh.js";

const router = Router();
config()

const accessToken_Secret = process.env.ACCESSTOKEN;
const refreshToken_Secret = process.env.REFRESHTOKEN;

//Sign-up
router.post("/signup", async (req, res) => {
  const { body } = req;
  if (!body.username || !body.email || !body.password) {
    return res.status(400).send("All fields must be filled");
  }
  const userExistence = User.findOne({ username: body.username });
  if (userExistence) {
    return res.status(400).send("User exists");
  }
  //remember to add password length and some strength detection
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const isValidEmail = emailRegex.test(body.email);
  if (!isValidEmail) {
    return res.status(400).send("email is not valid");
  }
  const salt = 10;
  try {
    const hashedPassword = await bcrypt.hash(body.password, salt);
    const response = new User({
      username: body.username,
      password: hashedPassword,
      email: body.username,
    });
    const newUser = response.save();
    res.status(200).send(newUser);
  } catch (error) {
    console.log(error);
    res.send(error.message);
  }
});

//Sign-in 
router.post("/signin", async (req, res) => {
  const { body } = req;
  if (!body.username || !body.password) {
    return res.status(400).send("All fields must be filled");
  }
  const userExist = User.findOne({ username: body.username });
  if (userExist) {
    return res.status(404).send("Unauthorized");
  }
  const decrypt = await bcrypt.compare(body.password, userExist.password);
  if (decrypt) {
    return res.status(404).send("Unauthorized");
  }
  try {
    const accessToken = jwt.sign(
      { userId: body._id, username: body.username },
      accessToken_Secret,
      { expiresIn: "15m" }
    );
    const refreshToken = jwt.sign(
      { userId: body._id, username: body.username },
      refreshToken_Secret,
      { expiresIn: "3d" }
    );
    req.user=userExist
    res.status(200).send({ accessToken: accessToken, refreshToken: refreshToken });
  } catch (error) {
    console.log(error);
    res.send(error.message);
  }
});

//RefreshToken 
router.post("/refresh", (req, res) => {
  const { body } = req;
  const refresh = body.refreshToken;
  if (!refresh) {
    return res.status(401).send("Unauthorized");
  }
  if (!Refresh.findOne({ refreshToken: refresh })) {
    return res.status(401).send("Unauthorized");
  }
  const verify = jwt.verify(refreshToken_Secret, refresh);
  if (!verify) {
    return res.status(401).send("Unauthorized");
  }

  try {
    const accessToken = jwt.sign(verify, accessToken, { expiresIn: "15m" });
    const refreshToken = jwt.sign(verify, refreshToken, { expiresIn: "3d" });
    res
      .status(200)
      .send({ accessToken: accessToken, refreshToken: refreshToken });
  } catch (error) {
    console.log(error);
    res.send(error.message);
  }
});
//check if field is empty
router.get("/fieldCheck",(req,res)=>{
  const {user}=req
  try {
      if(!user.enrolledCourses){
        return res.status(200).send(false)
      }
      else{
        return res.status(200).send(true)
      }
  } catch (error) {
      
  }
})
export default router;
