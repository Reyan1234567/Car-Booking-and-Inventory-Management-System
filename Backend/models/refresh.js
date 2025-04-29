import mongoose from "mongoose"


const refreshSchema=new mongoose.Schema({
    refreshToken:{
        type:String,
        required:true,
        unique:true
    },
    userId:{
        type:mongoose.Schema.Types.ObjectId,
    },
    createdAt:{
        type:Date,
        default:()=>{new Date()}
    },
    expiresAt:{
        type:Date,
        default:()=>new Date(Date.now() + 3 * 24 * 60 * 60 * 1000)
    }

},{timeStamps:true})

refreshSchema.index({"expiresAt":1},{expireAfterSeconds:0})


const refreshToken=mongoose.model("refreshes",refreshSchema)

export default refreshToken