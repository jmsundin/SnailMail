import { test, expect, request } from '@playwright/test'

test.beforeEach(async ({ page }) => {
    await page.goto('/')
})

// simple check for successful GET request for inbox page
test('successful GET request for inbox page', async ({ page }) => {
    const response = await page.waitForResponse('http://localhost:8080/mail')
    expect(response.status()).toBe(200)
})

// check for successful GET request for inbox page sent directly to backend
test('should display the inbox page', async () => {
    const requestContext = await request.newContext()
    const response = await requestContext.get('http://localhost:8080/mail')
    expect(response.status()).toBe(200)
    await requestContext.dispose()
})