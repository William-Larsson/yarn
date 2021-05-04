// Setup:
// Install PeerJS globally through the terminal with;
// sudo npm i peer -g

// Run PeerJS inside this folder:
// peerjs --port 9000 --key peerjs 

// Run PeerJS from another folder (example)
// peerjs --port 9000 --key peerjs --path /webrtc-project-test


//const { Peer } = require("./peerjs")

let localVideo = document.getElementById("local-video")
let remoteVideo = document.getElementById("remote-video")

// "hide" the video players to avoid ugly "play video" interface
localVideo.style.opacity = 0
remoteVideo.style.opacity = 0

// listen for onplaying events and set opacity back to normal
localVideo.onplaying = () => {
    localVideo.style.opacity = 1
}
remoteVideo.onplaying = () => {
    remoteVideo.style.opacity = 1
}


// Init connection. UserId will be sent to the
// PeerJS server for the given peer.
let peer
function init(userId) {
    peer = new Peer(userId, {
        host: '192.168.1.203', // get with: ifconfig | grep inet
        port: 9000,
        path: '/' // this need to be the same path as the PeerJS server (see Run above)
    })

    peer.on('open', () => {
        // TODO: This will call a function in hte Android code later
    })

    listen()
}

let localStream
function listen(){

    // On a call request
    peer.on('call', (call) => {
        // Get audio and video
        navigator.getUserMedia({
            audio: true,
            video: true
        }, 
        (stream) => {
            localVideo.srcObject = stream
            localStream = stream
            // Answer call and send local stream to the caller 
            call.answer(stream)
            // When call is answered, handle the incoming remote video stream
            call.on('stream'), (remoteStream) => {
                remoteVideo.srcObject = remoteStream
                // change position of videos in the HTML to get remote video as the large video
                remoteVideo.className = "primary-video"
                localVideo.className = "secondary-video"
            }
        })
    })
}

// Start a call with a remote user. 
function startCall(remoteUserId) {
    navigator.getUserMedia({
        audio: true,
        video: true
    }),
    (stream) => {
        localVideo.srcObject = stream
        localStream = stream
        // Send a call request to the remote user by their ID
        const call = peer.call(remoteUserId, stream)
        // Once the remote user accept the call..
        call.on('stream', (remoteStream) => {
            // TODO: this is the same as in listen() --> make a func
            remoteVideo.srcObject = remoteStream
            // change position of videos in the HTML to get remote video as the large video
            remoteVideo.className = "primary-video"
            localVideo.className = "secondary-video"
        })
    }
}

function toggleVideo(toggle) {
    if (toggle == "true") {
        localStream.getVideoTracks()[0].enabled = true
    } else {
        localStream.getVideoTracks()[0].enabled = false
    }
}

function toggleAudio(toggle) {
    if (toggle == "true") {
        localStream.getAudioTracks()[0].enabled = true
    } else {
        localStream.getAudioTracks()[0].enabled = false
    }
}

