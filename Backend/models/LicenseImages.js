import mongoose from 'mongoose';

const licenseImageSchema = new mongoose.Schema({
    filename: String,
    path: String,
    size: Number,
    uploadDate: { type: Date, default: Date.now }
});

const licenseImage = mongoose.model('LicenseImage', licenseImageSchema);
export default licenseImage