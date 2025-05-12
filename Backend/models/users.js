import mongoose from "mongoose";

const userSchema = new mongoose.Schema({
  username: {
    required: true,
    type: String,
    unique: true,
    trim: true,
  },
  email: {
    required: true,
    type: String,
    unique: true,
    trim: true,
  },
  password: {
    required: true,
    type: String,
    trim: true,
  },
  role:{requried:true, type:String, default:"user"},
  firstName: { type: String, required: true },
  lastName: { type: String, required: true },
  phoneNumber: { type: String, required: true, trim:true, unique:true },
  // licenseNumber: { type: String},
  // licenseExpiry: { type: Date },
  licensePhoto:{type:mongoose.Schema.Types.ObjectId, ref:"licensePhoto", },
  profilePhoto:{type:mongoose.Schema.Types.ObjectId, ref:"profilePhoto", },
  birthDate: { type: String, required :true},
  history: [
    { bookingId:{
      type: mongoose.Schema.Types.ObjectId,
      ref: "Booking",
      default:[]
    }},
  ],
});

const User = mongoose.model("user", userSchema);
export default User;
