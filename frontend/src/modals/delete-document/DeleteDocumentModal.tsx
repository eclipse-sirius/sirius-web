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
`;

const propTypes = {
  documentName: PropTypes.string.isRequired,
  documentId: PropTypes.string.isRequired,
  onDocumentDeleted: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const DeleteDocumentModal = ({ documentName, documentId, onDocumentDeleted, onClose }) => {
  const [errorMessage, setErrorMessage] = useState('');
  const [performDocumentDeletion, { loading, data, error }] = useMutation(deleteDocumentMutation);
  const onDeleteDocument = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        documentId,
      },
    };
    performDocumentDeletion({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        setErrorMessage(error.message);
      } else if (data?.deleteDocument) {
        const { deleteDocument } = data;
        if (deleteDocument.__typename === 'DeleteDocumentSuccessPayload') {
          onDocumentDeleted();
        } else if (deleteDocument.__typename === 'ErrorPayload') {
          setErrorMessage(deleteDocument.message);
        }
      }
    }
  }, [loading, data, error, onDocumentDeleted]);

  let bannerContent = errorMessage ? <Banner data-testid="banner" content={errorMessage} /> : null;
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
        <Buttons>
          <DangerButton type="button" onClick={onDeleteDocument} label="Delete" data-testid="delete-document" />
        </Buttons>
      </div>
    </Modal>
  );
};
DeleteDocumentModal.propTypes = propTypes;
