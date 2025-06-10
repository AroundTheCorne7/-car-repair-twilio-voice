# 🚂 Railway Deployment Instructions

## Your project is ready for deployment! Here's what to do:

### Step 1: Create GitHub Repository

1. **Go to**: https://github.com/new
2. **Repository name**: `car-repair-twilio-voice` (or any name you prefer)
3. **Make it Public** (for free Railway deployment)
4. **Don't check** "Add a README file" (we already have one)
5. **Click "Create repository"**

### Step 2: Push to GitHub

Copy and run these commands in your terminal (replace YOUR_USERNAME with your GitHub username):

```bash
git remote add origin https://github.com/YOUR_USERNAME/car-repair-twilio-voice.git
git branch -M main
git push -u origin main
```

### Step 3: Deploy to Railway

1. **Go to**: https://railway.app/
2. **Sign up/Login** with your GitHub account
3. **Click "New Project"**
4. **Select "Deploy from GitHub repo"**
5. **Choose your repository** (`car-repair-twilio-voice`)
6. **Railway will automatically**:
   - Detect it's a Java/Maven project
   - Build your application
   - Deploy it
   - Give you a public URL (e.g., `https://your-app.railway.app`)

### Step 4: Configure Environment Variables

1. **In Railway dashboard**, click on your project
2. **Go to "Variables" tab**
3. **Add these variables**:
   ```
   TWILIO_ACCOUNT_SID=your_account_sid_here
   TWILIO_AUTH_TOKEN=your_auth_token_here
   TWILIO_PHONE_NUMBER=your_twilio_phone_number_here
   ```

### Step 5: Update application.properties

Update your `src/main/resources/application.properties` to use environment variables:

```properties
# Twilio Configuration
twilio.account.sid=${TWILIO_ACCOUNT_SID}
twilio.auth.token=${TWILIO_AUTH_TOKEN}
twilio.phone.number=${TWILIO_PHONE_NUMBER}
```

### Step 6: Configure Twilio Webhook

1. **Copy your Railway URL** (e.g., `https://your-app.railway.app`)
2. **Go to**: https://console.twilio.com/
3. **Navigate to**: Phone Numbers → Manage → Active numbers
4. **Click on**: `+14097528224`
5. **In Voice section**, set webhook URL to:
   ```
   https://your-app.railway.app/api/voice/incoming
   ```
6. **Save configuration**

### Step 7: Test Your Deployment

1. **Test health endpoint**: `https://your-app.railway.app/api/voice/health`
2. **Call your Twilio number**: `+14097528224`
3. **You should hear**: "Здравейте! Свързахте се със сервиза..."

## 🎉 That's it! Your Twilio voice integration is live!

### Troubleshooting

- **Check Railway logs** if deployment fails
- **Verify environment variables** are set correctly
- **Test the health endpoint** first before configuring Twilio
- **Check Twilio webhook logs** in Twilio Console

### Next Steps

Once deployed, you can:
- Add more voice features (recording, speech-to-text)
- Integrate with your booking system
- Add call routing and IVR menus
- Monitor call analytics in Twilio Console
