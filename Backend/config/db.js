import mongoose from "mongoose";
import { config } from "dotenv";
config();

const connect = async () => {
  await mongoose.connect(process.env.DB)
    .then(()=>console.log("Mongodb Connected"))
    .catch(err=>console.log(`Connectrion Error:${err}`))
};


export default connect