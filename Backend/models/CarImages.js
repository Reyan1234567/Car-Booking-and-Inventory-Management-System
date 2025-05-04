import mongoose from 'mongoose';

const carImageSchema = new mongoose.Schema({
    filename: String,
    path: String,
    size: Number,
    uploadDate: { type: Date, default: Date.now }
});

const carImage = mongoose.model('Image', profileImageSchema);
export default carImage