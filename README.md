**VitoLearn Backend**

**Problem Statement**:

In today's educational landscape, students often face challenges in connecting with peers who share similar academic interests and expertise in their field of study. The lack of a dedicated platform for collaborative learning and knowledge-sharing hinders students' ability to easily ask questions, seek guidance, and engage in meaningful discussions with their peers. Additionally, traditional communication channels, such as social media, lack the focus and structure required for effective academic discourse. There is a pressing need for a specialized software solution that caters to students' academic needs by providing a platform for the creation of a student community where they can freely share questions, insights, and solutions related to their respective fields of study.

**Solution:**

To address the identified challenges, we propose the development of a comprehensive student community software, tentatively named "PeerLearn." PeerLearn aims to foster a collaborative learning environment by connecting students with similar academic interests and facilitating the exchange of knowledge within a structured and focused platform. The key features of PeerLearn include:


1. **User Profiles and Academic Interests:**
   - users can create profiles highlighting their academic background, interests, and expertise.
     
2. **Topic-specific Communities:**
   - Organized channels for different academic disciplines or courses to streamline discussions.
     
3. **Communities request and admin approval to go live:**
   - Communities requested by user and admin can approve through admin panel

4. **Articles creation through communities:**
   - articles can be created using rich editor and be live

5. **Peers discuss about article in comment section and either like or love:**
   - giving feedback to the creator is a big motivation


   
   **VitoLearn Backend Tech**

Technology Stack: Spring Boot framework
security: Spring security
Database: Postegresql


**Authentication**:

1. User login
2. We receive jwt token set cookie
3. We use authContext in our app to keep track of user auth
4. We send request isuserloggedIn to make sure the user token is valid
5. After backend send us isvalid we request the userInfo and store in redux
6. We can then use this info across app

   ER Diagram
   
<img width="843" alt="Screenshot 2023-12-18 at 18 13 04" src="https://github.com/Elvito-hub/vitolearn-backend/assets/71209569/e9513302-970d-421e-a597-942ece81a9ee">
