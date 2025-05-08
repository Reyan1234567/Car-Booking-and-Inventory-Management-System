import mongoose from 'mongoose';

const carImageSchema = new mongoose.Schema({
    filename: String,
    path: String,
    size: Number,
    url:String,
    uploadDate: { type: Date, default: Date.now }
});

const carImage = mongoose.model('carImage', carImageSchema);
export default carImage