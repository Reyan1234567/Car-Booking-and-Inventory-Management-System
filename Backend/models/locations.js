import mongoose from "mongoose"

const locationSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true
  },
  city: {
    type: String,
    required: true
  },
  country: {
    type: String,
    default: "Ethiopia"
  },
  type: {
    type: String,
    enum: ["city", "airport", "landmark"],
    required: true
  },
  searchTerms: {
    type: [String],
    default: []
  }
}, { timestamps: true });

const locations= mongoose.model('Location', locationSchema);

export default locations