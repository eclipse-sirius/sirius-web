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
import { useMutation } from '@apollo/client';
import { Banner } from 'core/banner/Banner';
import { Buttons, DangerButton } from 'core/button/Button';
import { Text } from 'core/text/Text';
import gql from 'graphql-tag';
import { Modal } from 'modals/Modal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import styles from './DeleteProjectModal.module.css';

const deleteProjectMutation = gql`
  mutation deleteProject($input: DeleteProjectInput!) {
    deleteProject(input: $input) {
      __typename
      ... on DeleteProjectSuccessPayload {
        viewer {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const propTypes = {
  projectId: PropTypes.string.isRequired,
  onProjectDeleted: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const DeleteProjectModal = ({ projectId, onProjectDeleted, onClose }) => {
  const [errorMessage, setErrorMessage] = useState('');
  const [performProjectDeletion, { loading, data, error }] = useMutation(deleteProjectMutation);
  const onDeleteProject = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        projectId,
      },
    };
    performProjectDeletion({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        setErrorMessage(error.message);
      } else if (data?.deleteProject) {
        const { deleteProject } = data;
        if (deleteProject.__typename === 'DeleteProjectSuccessPayload') {
          onProjectDeleted();
        } else if (deleteProject.__typename === 'ErrorPayload') {
          setErrorMessage(deleteProject.message);
        }
      }
    }
  }, [loading, data, error, onProjectDeleted]);

  let bannerContent = null;
  if (errorMessage) {
    bannerContent = <Banner data-testid="banner" content={errorMessage} />;
  }
  return (
    <Modal title="Delete the project" onClose={onClose}>
      <div className={styles.container}>
        <div className={styles.content}>
          <Text className={styles.subtitle}>
            This action will delete everything in the project, it cannot be reversed.
          </Text>
          <div className={styles.bannerArea}>{bannerContent}</div>
        </div>
        <Buttons>
          <DangerButton type="button" onClick={onDeleteProject} label="Delete" data-testid="delete-project" />
        </Buttons>
      </div>
    </Modal>
  );
};
DeleteProjectModal.propTypes = propTypes;
