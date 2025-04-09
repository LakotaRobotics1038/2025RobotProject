# Environmental Lighting - Adjusting the Camera(s) For Vision
## Lighting will be different at every event. If vision is to be used, camera(s) must be adjusted for accuracy of position & object recognition. 
## This guide/reference was written for the 2025, game Reefscape and so information written may be out of date.

# Physical Setup
## Below is the setup steps & recommendations for before you start tweaking any settings on [PhotonVision](https://photonvision.org/)
- Step 1, put the robot in view of 2 apriltags and an algae, 1 apriltag must face the front camera and the other apriltag & the algae must face the back camera
  - Step 2, once the apriltags and algae are in view of the cameras, plug an ethernet cable into the RoboRio & a laptop that you have with you
- Step 3, plug in a battery and turn on the robot as well as the computer
- Step 4, once logged into the computer go to the [PhotonVision Dashboard](http://photonvision.local:5800/#/dashboard)
- You are now down with the physical setup onto the adjusting phase

# Adjusting in PhotonVision
## There are 3 total pipelines in PV and **ALL** of them need to be adjusted for the field lighting. The FrontCamera has 1 pipeline and the BackCamera has 2 pipelines. You will be adjusting SOME of the settings in the Input tab for each pipeline. Below is information on what should be changed, what shouldn't, what to avoid, and what all the words in the dashboard mean for each pipeline / camera.
### DO NOT CHANGE ANY OF FOLLOWING VALUES
- Orientation
- Resolution
- Stream Resolution
- Anything in the Apriltag and Output tabs

### Values To Adjust And What They Do
- Auto Exposure - **TURN OFF FOR COMPETITIONs** This adusts the shutter of the camera meaning more light hits the camera but it does this on it's own and isn't exactly reliable and can cause weird effects to the camera.
- Exposure - Ideally set the exposure low (left side of the slider) as the lower the exposure **generally** the faster PV will be able to recognize objects and Apriltags, if you'd like more information click [here](https://docs.photonvision.org/en/v2025.3.1/docs/pipelines/input.html)
- Gain
- Brightness
