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
  firstName: { type: String, required: true },
  lastName: { type: String, required: true },
  phoneNumber: { type: String, required: true, trim:true, unique:true },
  // licenseNumber: { type: String},
  // licenseExpiry: { type: Date },
  licensePhoto:{type:String, default:"Not set yet"},
  profilePhoto:{type:String, default:"Not set yet"},
  birthDate: { type: String, required :true},
  history: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Booking",
      default:[]
    },
  ],
});

const User = mongoose.model("user", userSchema);
export default User;
