/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { useMutation } from 'common/GraphQLHooks';
import { ActionButton } from 'core/button/Button';
import { Form } from 'core/form/Form';
import { Label } from 'core/label/Label';
import { Link } from 'core/link/Link';
import { Message } from 'core/message/Message';
import { Textfield } from 'core/textfield/Textfield';
import React, { useEffect, useReducer } from 'react';
import { Redirect } from 'react-router-dom';
import { FormContainer } from 'views/FormContainer';
import { View } from 'views/View';
import { HANDLE_CHANGE_NAME__ACTION, HANDLE_SUBMIT__ACTION, VALID__STATE } from './machine';
import styles from './NewProjectView.module.css';
import { initialState, reducer } from './reducer';
import gql from 'graphql-tag';

const createProjectMutation = gql`
  mutation createProject($input: CreateProjectInput!) {
    createProject(input: $input) {
      __typename
      ... on CreateProjectSuccessPayload {
        project {
          id
          owner {
            id
            username
          }
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

/**
 * Communicates with the server to handle the operations related to the
 * new project page.
 *
 * @author hmarchadour
 * @author sbegaudeau
 * @author lfasani
 */
export const NewProjectView = () => {
  const [createProject, projectCreationResult] = useMutation(createProjectMutation, {}, 'createProject');
  const [state, dispatch] = useReducer(reducer, initialState);

  const { viewState, name, nameMessage, nameMessageSeverity, message, newProjectId } = state;

  const onNameChange = (event) => {
    const name = event.target.value;
    dispatch({ type: HANDLE_CHANGE_NAME__ACTION, name });
  };

  const onCreateNewProject = async (event) => {
    event.preventDefault();

    const variables = {
      input: {
        name: name.trim(),
        visibility: 'PUBLIC',
      },
    };
    createProject(variables);
  };

  useEffect(() => {
    if (!projectCreationResult.loading) {
      dispatch({ type: HANDLE_SUBMIT__ACTION, response: projectCreationResult });
    }
  }, [projectCreationResult]);

  if (newProjectId) {
    return <Redirect to={`/projects/${newProjectId}/edit`} />;
  }

  const canBeSubmitted = viewState === VALID__STATE;
  const nameMessageContent = nameMessage ? (
    <Message severity={nameMessageSeverity} data-testid="nameMessage" content={nameMessage} />
  ) : null;
  return (
    <View condensed>
      <FormContainer title="Create a new project" subtitle="Get started by creating a new project" banner={message}>
        <Form onSubmit={onCreateNewProject}>
          <Label value="Name">
            <Textfield
              name="name"
              placeholder="Enter the project name"
              value={name}
              onChange={onNameChange}
              autoFocus
              data-testid="name"
            />
            <div className={styles.messageArea}>{nameMessageContent}</div>
          </Label>
          <ActionButton type="submit" disabled={!canBeSubmitted} label="Create project" data-testid="create-project" />
          <div className={styles.links}>
            <Link to="/" data-testid="back">
              Back to homepage
            </Link>
          </div>
        </Form>
      </FormContainer>
    </View>
  );
};
