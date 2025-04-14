import mongoose from "mongoose";

const bookingSchema = new mongoose.Schema({
  bookingId: { type: String, required: true },
  bookingTimestamp: { type: Date, required: true },
  bookingStatus: {
    type: String,
    required: true,
    enum: ["Confirmed", "Pending", "Cancelled", "Waiting Approval"],
  },
  driver: {
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "User",
      required: true,
    },
  },
  car: {
    carId: { type: mongoose.Schema.Types.ObjectId, ref: "Car", required: true },
  },
  pickupDate: { type: Date, required: true },
  pickupTime: { type: String, required: true },
  dropoffDate: { type: Date, required: true },
  dropoffTime: { type: String, required: true },
  pickupLocationId: { type: String, required: true },
  pickupLocationName: { type: String, required: true },
  dropoffLocationId: { type: String, required: true },
  dropoffLocationName: { type: String, required: true },
  baseRate: { type: Number, required: true },
  additionalFees: {
    airportSurcharge: { type: Number, default: 0 },
  },
  estimatedTotalPrice: { type: Number, required: true },
  paymentMethod: {
    type: String,
    required: true,
    enum: ["Credit Card", "Cash", "Bank Transfer"],
  },
  paymentStatus: {
    type: String,
    required: true,
    enum: ["Paid", "Unpaid", "Pending"],
  },
  optionalExtras: {
    childSeat: { type: Number, default: 0 },
    gpsNavigation: { type: Boolean, default: false },
  },
});

const Booking = mongoose.model("Booking", bookingSchema);

export default Booking;
