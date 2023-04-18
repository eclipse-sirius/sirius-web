const { defineConfig } = require('cypress')

module.exports = defineConfig({
  screenshotsFolder: 'target/screenshots',
  video: false,
  reporter: 'junit',
  reporterOptions: {
    mochaFile: 'target/result-[hash].xml',
    toConsole: true,
  },
  viewportWidth: 1920,
  viewportHeight: 1080,
  defaultCommandTimeout: 60000,
  requestTimeout: 60000,
  responseTimeout: 60000,
  env: {
    baseAPIUrl: 'http://localhost:8080',
  },
  e2e: {
    setupNodeEvents(on, config) {
      return require('./cypress/plugins/index.js')(on, config)
    },
    baseUrl: 'http://localhost:8080',
  },
})
