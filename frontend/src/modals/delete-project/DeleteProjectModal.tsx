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
`.loc.source.body;

const propTypes = {
  projectId: PropTypes.string.isRequired,
  onProjectDeleted: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const DeleteProjectModal = ({ projectId, onProjectDeleted, onClose }) => {
  const [error, setError] = useState('');
  const [performProjectDeletion, deleteProjectResult] = useMutation(deleteProjectMutation, {}, 'deleteProject');
  const onDeleteProject = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        projectId,
      },
    };
    performProjectDeletion(variables);
  };

  useEffect(() => {
    if (!deleteProjectResult.loading) {
      const { data, error } = deleteProjectResult;
      if (error) {
        setError(error.message);
      } else {
        const { deleteProject } = data.data;
        if (deleteProject.__typename === 'DeleteProjectSuccessPayload') {
          onProjectDeleted();
        } else if (deleteProject.__typename === 'ErrorPayload') {
          setError(deleteProject.message);
        }
      }
    }
  }, [deleteProjectResult, onProjectDeleted]);

  let bannerContent = null;
  if (error) {
    bannerContent = <Banner data-testid="banner" content={error} />;
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
