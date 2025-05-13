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

// simluate a failed GET request for the inbox page to see how the frontend handles it
test('shows error when GET /mail fails', async ({ page }) => {
    // Intercept the GET request and force a failure
    await page.route('http://localhost:8080/mail', route => {
        route.abort() // Simulates a network failure
    })

    await page.goto('/')

    // Listen for the alert dialog
    page.once('dialog', async (dialog) => {
        expect(dialog.message()).toContain('problem when fetching your inbox');
        await dialog.dismiss() // Close the alert
    })

    await page.goto('/')
});

// simulate there being no mail in the inbox
test('shows message when there is no mail in the inbox', async ({ page }) => {
    await page.route('http://localhost:8080/mail', route => {
        route.fulfill({
            status: 200,
            body: JSON.stringify([])
        })
    })

    await page.goto('/')
    await expect(page.getByText('No Mail! You\'re all caught up!')).toBeVisible()
})

// check that the Compose component is visible when the Compose Email button is clicked
test('shows compose componennt when compose button is clicked', async ({ page }) => {
    await page.getByRole('button', { name: 'Compose Email' }).click()
    await expect(page.getByRole('heading', { name: 'Compose Email' })).toBeVisible()
})