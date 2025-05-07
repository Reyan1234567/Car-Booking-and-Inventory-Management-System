import mongoose from 'mongoose';

const licenseImageSchema = new mongoose.Schema({
    filename: String,
    path: String,
    size: Number,
    url:String,
    uploadDate: { type: Date, default: Date.now }
});

const licensePhoto = mongoose.model('LicensePhotos', licenseImageSchema);
export default licensePhoto