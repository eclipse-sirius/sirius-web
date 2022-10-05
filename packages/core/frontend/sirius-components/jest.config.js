module.exports = {
  preset: 'ts-jest',
  verbose: true,
  testEnvironment: 'jsdom',
  modulePaths: ['src'],
  testResultsProcessor: './node_modules/jest-junit-reporter',
  testPathIgnorePatterns: ['<rootDir>/dist/', '<rootDir>/node_modules/'],
  transform: {
    '^.+\\.js$': 'babel-jest',
    '^.+\\.css$': 'jest-transform-css',
  },
  moduleNameMapper: {
    '\\.(css|less)$': '<rootDir>/__mocks__/styleMock.js',
    d3: '<rootDir>/node_modules/d3/dist/d3.min.js',
  },
};
