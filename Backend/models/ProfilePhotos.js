import mongoose from 'mongoose';

const profilePhotoSchema = new mongoose.Schema({
    filename: String,
    path: String,
    size: Number,
    url:String,
    uploadDate: { type: Date, default: Date.now }
});

const profilePhoto = mongoose.model('profilePhoto', profilePhotoSchema);
export default profilePhoto