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
import { ActionButton, DangerButton } from 'core/button/Button';
import { Text } from 'core/text/Text';
import gql from 'graphql-tag';
import { Modal } from 'modals/Modal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import styles from './DeleteDocumentModal.module.css';

const deleteDocumentMutation = gql`
  mutation deleteDocument($input: DeleteDocumentInput!) {
    deleteDocument(input: $input) {
      __typename
      ... on DeleteDocumentSuccessPayload {
        project {
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
  documentName: PropTypes.string.isRequired,
  documentId: PropTypes.string.isRequired,
  onDocumentDeleted: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const DeleteDocumentModal = ({ documentName, documentId, onDocumentDeleted, onClose }) => {
  const [error, setError] = useState('');
  const [performDocumentDeletion, deleteDocumentResult] = useMutation(deleteDocumentMutation, {}, 'deleteDocument');
  const onDeleteDocument = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        documentId,
      },
    };
    performDocumentDeletion(variables);
  };

  useEffect(() => {
    if (!deleteDocumentResult.loading) {
      const { data, error } = deleteDocumentResult;
      if (error) {
        setError(error.message);
      } else {
        const { deleteDocument } = data.data;
        if (deleteDocument.__typename === 'DeleteDocumentSuccessPayload') {
          onDocumentDeleted();
        } else if (deleteDocument.__typename === 'ErrorPayload') {
          setError(deleteDocument.message);
        }
      }
    }
  }, [deleteDocumentResult, onDocumentDeleted]);

  let bannerContent = error ? <Banner data-testid="banner" content={error} /> : null;
  return (
    <Modal title={`Permanently delete "${documentName}"`} onClose={onClose}>
      <div className={styles.container}>
        <div className={styles.content}>
          <Text className={styles.subtitle}>
            This action will permanently delete the document and all its associated content. These items will no longer
            be accessible to you or anyone else. This action is irreversible.
          </Text>
          <div className={styles.bannerArea}>{bannerContent}</div>
        </div>
        <DangerButton type="button" onClick={onDeleteDocument} label="Delete" data-testid="delete-document" />
        <ActionButton type="button" onClick={onClose} label="Cancel" data-testid="cancel" />
      </div>
    </Modal>
  );
};
DeleteDocumentModal.propTypes = propTypes;
