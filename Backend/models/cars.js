import mongoose from "mongoose"

const carSchema = new mongoose.Schema({
  name: { type: String, required: true },
  make: { type: String, required: true },
  price: { type: Number, required: true },
  model: { type: String, required: true },
  year: { type: Number, required: true },
  category: { type: String, required: true },
  transmissionType: { type: String, required: true },
  fuelType: { type: String, required: true },
  passengerCapacity: { type: Number, required: true },
  luggageCapacity: { type: String, required: true },
  imageUrl: { type: String, required: true },
  dailyRate: { type: Number, required: true },
  hourlyRate: { type: Number, required: true },
  availabilityStatus: { type: String, required: true, enum: ['Available', 'Unavailable'] }
});

const Car = mongoose.model('Car', carSchema);

export default Car