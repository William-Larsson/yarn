// Setup:
// Install PeerJS globally through the terminal with;
// sudo npm i peer -g
//
// Run PeerJS inside this folder:
// peerjs --port 5000 --key peerjs 
//
// Run PeerJS from another folder (example)
// peerjs --port 5000 --key peerjs --path /webrtc-project-test

let localVideo = document.getElementById("local-video")
let remoteVideo = document.getElementById("remote-video")

// "hide" the video players to avoid ugly "play video" interface
localVideo.style.opacity = 0
remoteVideo.style.opacity = 0

// listen for onplaying events and set opacity back to normal
localVideo.onplaying = () => { localVideo.style.opacity = 1 }
remoteVideo.onplaying = () => { remoteVideo.style.opacity = 1 }

// Init connection. UserId will be sent to the
// PeerJS server for the given peer.
let peer
function init(userId) {
    peer = new Peer(userId, {
        host: '192.168.1.56', // NEED TO BE CHANGED MANUALLY - get with: ifconfig | grep inet
        port: 9000,
        path: '/'  // needs to be the same path as the PeerJS server (see Run above)
    })

    initCallListener()
}

// Listen for call events from remote peer and connect the 
// local and remote video/audio streams to the 
// video-tags in the HTML. 
let localStream
function initCallListener() {
    // On a call request event
    peer.on('call', (call) => {
        // Request to get access to audio and video
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
            call.on('stream', (remoteStream) => {
                remoteVideo.srcObject = remoteStream
                // change position of videos in the HTML to get remote video as the large video
                remoteVideo.className = "primary-video"
                localVideo.className = "secondary-video"
            })
        })
    })
}

// Start a call from the local peer connecting to remote
// peer of given user ID. 
function startCall(otherUserId) {
    navigator.getUserMedia({
        audio: true,
        video: true
    }, 
    (stream) => {
        localVideo.srcObject = stream
        localStream = stream

        // Call the remote peer, include local video/audio stream
        const call = peer.call(otherUserId, stream)

        call.on('stream', (remoteStream) => {
            remoteVideo.srcObject = remoteStream

            remoteVideo.className = "primary-video"
            localVideo.className = "secondary-video"
        })
    })
}

// Toggle video feed on/off
function toggleVideo(b) {
    if (b == "true") {
        localStream.getVideoTracks()[0].enabled = true
    } else {
        localStream.getVideoTracks()[0].enabled = false
    }
} 

// Toggle audio feed on/off
function toggleAudio(b) {
    if (b == "true") {
        localStream.getAudioTracks()[0].enabled = true
    } else {
        localStream.getAudioTracks()[0].enabled = false
    }
} 
