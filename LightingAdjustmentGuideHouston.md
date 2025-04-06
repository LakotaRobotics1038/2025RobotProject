# Environmental Lighting - Adjusting the Camera(s) For Vision
## Lighting will be different at every event. If vision is to be used, camera(s) must be adjusted for accuracy of position & object recognition.
### General instructions
- Place the robot on the field
  - 2025 Reefscape specific instructions:
  - The cameras need to view two apriltags and one algae
  - One apriltag must be in view of each camera
  - Obtain one Algae object if possible

#### Cameras and Pipelines
A robot may have multiple cameras and each camera will have at least one pipeline, maybe more depending on the use case. Cameras are considered the hardware on the robot.
Each pipeline will be processed by PhotonVision when called by the robot. 
- For 2025 Reefscape there are two cameras are installed on the robot (Front Camera & Back Camera)
  - The front camera has one pipeline for the recognition of Apriltags
  - The back camera has two pipelines, one for Apriltag recognition, one for Object Recognition (Algae)
  - Because there are three pipelines total, each pipeline must have the "input" settings of the camera adjusted for optimal performance.

### PhotonVision Steps
- Turn on the Robot
- Connect your computer to PhotonVision web interface (http://photonvision.local:5800)
- The left side of the PhotonVision interface is the navigation bar. By default you will be on the Dashboard.
- Camera and Pipeline Selection
  - The top right of the Dashboard displays the current Camera and Pipeline.

**The goal is to adjust the Exposure and Brightness values for optimal pipeline target recognition**

General Camera and Pipeline Settings
- Camera: Back Camera
- Pipeline: April Tag
- Type: April Tag
- Processing Mode: 3D
- Stream Display: Processed
  
- Near the bottom center of the PV Dashboard you will find the settings for the camera and the current pipeline.
- *When you change a setting, the value is automatically saved.*
 - Input tab (camera)
  - Auto Exposure: OFF (Typically set to this value)
  - Exposure: *Adjust as needed to help better recognize the AprilTag that is in view of the camera.*
  - Brightness: *Adjust as needed to help better recognize the AprilTag that is in view of the camera.*
  - Camera Gain: *Adjust as needed to help better recognize the AprilTag that is in view of the camera.*
  - Auto White Balance: OFF
  - Orientation: Normal
  - Resolution: 800x600 at 120FPS, MJPEG
  - Stream Resolution: 400x300
- AprilTag (Pipeline dependent)
- *Values on this tab should not be changed*
  - Target Family: AprilTag 36h11 (6.5in)
  - Decimate: 1
  - Blur: 1
  - Threads: 4
  - Decision Margin Cutoff: 35
  - Post estimation iterations: 40
  - Refine Edges: ON
- Output
- *Values on this tab should not be changed*
  - Show Multiple Targets: ON
  - Do Multi-Target Estimation: ON
  - Always do Single-Target Estimation: OFF
  - Target Offset Point: Center
  - Robot Offset Mode: None
  - Select the pipeline t
- To check if the cameras can actually see these, plug in an ethernet cable from the robot to one of our laptops and then open up [PhotonVisionLocal](http://photonvision.local:5800/#/dashboard)
- In the dashboard tab of Photonvision, first check if the robot can see the game pieces (apriltags & algae) by telling if IDs of the apriltags pop up and the number of algae is more than 0
- While still in the dashboard tab go to input and play around with the inputs in **EACH** pipeline and **EACH** Camera.
- Ideally you want to **avoid** the following: shaking contours drawn from the apriltags in 3D mode, flickering of detection (keeps appearing and disappearing), a "pulsating" effect of the camera (typically comes from auto exposure but sometimes doesn't), etc
- 
