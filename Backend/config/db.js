import mongoose from "mongoose";
import { config } from "dotenv";
config();

const connect = async () => {
    try {
      await mongoose.connect(process.env.DB, () => {
        console.log("database connected successfully");
      });
    
  } catch (error) {
    console.log(error)
  }
};


export default connect