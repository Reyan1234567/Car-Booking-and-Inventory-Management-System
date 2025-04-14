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
  contactNumber: { type: String, required: true },
  licenseNumber: { type: String, required: true },
  licenseExpiry: { type: Date, required: true },
  age: { type: Number, required: true },
  history: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Booking",
    },
  ],
});

const User = mongoose.model("user", userSchema);
export default User;
