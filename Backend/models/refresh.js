import mongoose from "mongoose"


const refreshSchema=new mongoose.Schema({
    refreshToken:{
        type:String,
        required:true,
        unique:true
    },
    userId:{
        type:mongoose.Schema.Types.ObjectId,
        required:true
    },
    createdAt:{
        type:Date,
        required:true
    },
    expiresAt:{
        type:Date,
        required:true
    }

},{timeStamps:true})

refreshSchema.index({"expiresAt":1},{expireAfterSeconds:0})


const refreshToken=mongoose.model("refreshes",refreshSchema)

export default refreshToken