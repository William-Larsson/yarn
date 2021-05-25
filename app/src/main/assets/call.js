// In order to run this you need to install PeerJS:
// sudo npm i peer -g
// Run PeerJS server on local network:
// peerjs --port 9000 --key peerjs --path /yarn

let localVideo = document.getElementById("local-video")
let remoteVideo = document.getElementById("remote-video")

// Init the video players to be invisible to avoid ugly "play video" UI
localVideo.style.opacity = 0
remoteVideo.style.opacity = 0

// When video is playing, make the video players visible again.
localVideo.onplaying = () => { localVideo.style.opacity = 1 }
remoteVideo.onplaying = () => { remoteVideo.style.opacity = 1 }

// Init the connection. UserId will be sent to the
// PeerJS server for the given peer.
let peer
function init(userId) {
    peer = new Peer(userId, {
        host: '192.168.10.179', // NOTE: change to IP of the current machine. Get with ifconfig | grep inet
        port: 9000,
        path: '/yarn' // Needs to be the same path as the PeerJS run command, see above.
    })

    peer.on('open', () => {
        // Call the method in the Android JS-interface class.
        Android.onPeerConnected()
    })

    initCallListener()

    return "true"
}

// Listen for the call events from remote peer and connect the
// local and remote video/audio streams to the video-tags in HTML
let localStream
function initCallListener() {
    // On a call request event.
    peer.on('call', (call) => {
        // Ask user to get access to audio/video
        navigator.getUserMedia({
            audio: true, 
            video: true
        }, (stream) => {
            localVideo.srcObject = stream
            localStream = stream
            // Answer the call and send local stream to the caller
            call.answer(stream)
            // When the call is answered, handle the incoming remote video stream
            call.on('stream', (remoteStream) => {
                remoteVideo.srcObject = remoteStream
                // Change position of the videos (local goes from primary to secondary/smaller)
                remoteVideo.className = "primary-video"
                localVideo.className = "secondary-video"
            })
        })
    })
}

// Start a call from the local peer connectin to remote
// peer of given user id.
function startCall(remoteUserId) {
    navigator.getUserMedia({
        audio: true,
        video: true
    }, (stream) => {
        localVideo.srcObject = stream
        localStream = stream
        // Call the remote peer, include local video/audio streams.
        const call = peer.call(remoteUserId, stream)
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
