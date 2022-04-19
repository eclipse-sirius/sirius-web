# Pull request template

## General purpose
What is the main goal of this pull request?
- [ ] Bug fixes
- [ ] New features
- [ ] Documentation
- [ ] Cleanup
- [ ] Tests
- [ ] Build / releng

## Project management
- [ ] Has the pull request been added to the relevant project and milestone? (Only if you know that your work is part of a specific iteration such as the current one)
- [ ] Have the `priority:` and `pr:` labels been added to the pull request? (In case of doubt, start with the labels `priority: low` and `pr: to review later`)
- [ ] Have the relevant issues been added to the pull request?
- [ ] Have the relevant labels been added to the issues? (`area:`, `difficulty:`, `type:`)
- [ ] Have the relevant issues been added to the same project and milestone as the pull request?
- [ ] Has the `CHANGELOG.adoc` been updated to reference the relevant issues?
- [ ] Have the relevant API breaks been described in the `CHANGELOG.adoc`? (Including changes in the GraphQL API)
- [ ] In case of a change with a visual impact, are there any screenshots in the `CHANGELOG.adoc`? For example in `doc/screenshots/2022.5.0-my-new-feature.png`

## Architectural decision records (ADR)
- [ ] Does the title of the commit contributing the ADR start with `[doc]`?
- [ ] Are the ADRs mentioned in the relevant section of the  `CHANGELOG.adoc`?

## Dependencies
- [ ] Are the new / upgraded dependencies mentioned in the relevant section of the `CHANGELOG.adoc`?
- [ ] Are the new dependencies justified in the `CHANGELOG.adoc`?

## Frontend

This section is not relevant if your contribution does not come with changes to the frontend.

### General purpose
- [ ] Is the code properly tested? (Plain old JavaScript tests for business code and tests based on React Testing Library for the components)

### Typing
We need to improve the typing of our code, as such, we require every contribution to come with proper TypeScript typing for both changes contributing new files and those modifying existing files.
Please ensure that the following statements are true for each file created or modified (this may require you to improve code outside of your contribution).

- [ ] Variables have a proper type
- [ ] Functions’ arguments have a proper type
- [ ] Functions’ return type are specified
- Hooks are properly typed:
	- [ ] `useMutation<DATA_TYPE, VARIABLE_TYPE>(…)`
	- [ ] `useQuery<DATA_TYPE, VARIABLE_TYPE>(…)`
	- [ ] `useSubscription<DATA_TYPE, VARIABLE_TYPE>(…)`
	- [ ] `useMachine<CONTEXT_TYPE, EVENTS_TYPE>(…)`
	- [ ] `useState<STATE_TYPE>(…)`
- [ ] All components have a proper typing for their props
- [ ] No useless optional chaining with `?.` (if the GraphQL API specifies that a field cannot be `null`, do not treat it has potentially `null` for example)
- [ ] Nullable values have a proper type (for example `let diagram: Diagram | null = null;`)

## Backend

This section is not relevant if your contribution does not come with changes to the backend.

### General purpose
- [ ] Are all the event handlers tested?
- [ ] Are the event processor tested?
- [ ] Is the business code (services) tested?
- [ ] Are diagram layout changes tested?

### Architecture
- [ ] Are data structure classes properly separated from behavioral classes?
- [ ] Are all the relevant fields final?
- [ ] Is any data structure mutable? If so, please write a comment indicating why
- [ ] Are behavioral classes either stateless or side effect free?

## Review

### How to test this PR?
_Please describe here the various use cases to test this pull request_

- [ ] Has the Kiwi TCMS test suite been updated with tests for this contribution?