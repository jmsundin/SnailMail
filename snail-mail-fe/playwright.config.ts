import { defineConfig } from '@playwright/test'; 

export default defineConfig({ 

  testDir: './playwright_tests', //the folder we made to hold our tests
    use: { 
    browserName: 'chromium', //or firefox or webkit! 
    headless: true, 
    screenshot: 'only-on-failure', 
    baseURL: 'http://localhost:5177' //base URL for React vite projects 
  }, 

}); 
 