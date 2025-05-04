import mongoose from 'mongoose';

const profileImageSchema = new mongoose.Schema({
    filename: String,
    path: String,
    size: Number,
    uploadDate: { type: Date, default: Date.now }
});

const profileImage = mongoose.model('Image', profileImageSchema);
export default profileImage