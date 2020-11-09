module.exports = {
  preset: 'ts-jest',
  verbose: true,
  testEnvironment: 'jsdom',
  modulePaths: ['src'],
  testResultsProcessor: './node_modules/jest-junit-reporter',
  testPathIgnorePatterns: ['<rootDir>/build/', '<rootDir>/node_modules/'],
  transform: {
    '^.+\\.js$': 'babel-jest',
    '^.+\\.css$': 'jest-transform-css',
  },
};
