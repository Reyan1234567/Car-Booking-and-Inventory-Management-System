const mongoose = require('mongoose');

const imageSchema = new mongoose.Schema({
    filename: String,
    path: String,
    size: Number,
    uploadDate: { type: Date, default: Date.now }
});

const Image = mongoose.model('Image', imageSchema);