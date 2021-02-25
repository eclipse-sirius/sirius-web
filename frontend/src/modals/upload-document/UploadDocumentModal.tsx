/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { sendFile } from 'common/sendFile';
import { ActionButton, Buttons } from 'core/button/Button';
import { FileUpload } from 'core/file-upload/FileUpload';
import { Form } from 'core/form/Form';
import gql from 'graphql-tag';
import { Modal } from 'modals/Modal';
import {
  HANDLE_SELECTED_ACTION,
  HANDLE_SUBMIT_ACTION,
  SELECTED_STATE,
  SUBMIT_SUCCESS_STATE,
} from 'modals/upload-document/machine';
import { initialState, reducer } from 'modals/upload-document/reducer';
import PropTypes from 'prop-types';
import React, { useEffect, useReducer } from 'react';
import { v4 as uuid } from 'uuid';

const uploadDocumentMutationFile = gql`
  mutation uploadDocument($input: UploadDocumentInput!) {
    uploadDocument(input: $input) {
      __typename
      ... on UploadDocumentSuccessPayload {
        document {
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
  onDocumentUploaded: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
export const UploadDocumentModal = ({ projectId, onDocumentUploaded, onClose }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, file } = state;

  // Execute the upload of a document and redirect to the newly created document
  const uploadDocument = async (event) => {
    event.preventDefault();
    const uploadDocumentMutation = uploadDocumentMutationFile.loc.source.body;
    const variables = {
      input: {
        id: uuid(),
        projectId,
        file: null, // the file will be send as a part of the multipart POST query.
      },
    };
    try {
      const response = await sendFile(uploadDocumentMutation, variables, file);
      const action = { type: HANDLE_SUBMIT_ACTION, response };
      dispatch(action);
    } catch (exception) {
      // Handle other errors like max file size error send by the backend...
      const action = { type: HANDLE_SUBMIT_ACTION, response: { error: exception.toString() } };
      dispatch(action);
    }
  };

  const onFileSelected = (file) => {
    const action = { type: HANDLE_SELECTED_ACTION, file };
    dispatch(action);
  };

  useEffect(() => {
    if (viewState === SUBMIT_SUCCESS_STATE) {
      onDocumentUploaded();
    }
  }, [viewState, onDocumentUploaded]);

  const canSubmit = viewState === SELECTED_STATE;
  return (
    <Modal title="Upload new model" onClose={onClose}>
      <Form onSubmit={uploadDocument} encType="multipart/form-data">
        <FileUpload onFileSelected={onFileSelected} data-testid="file" />

        <Buttons>
          <ActionButton type="submit" disabled={!canSubmit} label="Upload" data-testid="upload-document" />
        </Buttons>
      </Form>
    </Modal>
  );
};
UploadDocumentModal.propTypes = propTypes;
