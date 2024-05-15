/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { TreeToolBarContributionComponentProps } from '@eclipse-sirius/sirius-components-trees';
import PublishIcon from '@mui/icons-material/Publish';
import IconButton from '@mui/material/IconButton';

import { Fragment, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { UploadDocumentModal } from '../../../modals/upload-document/UploadDocumentModal';

type Modal = 'UploadDocument';

export const UploadDocumentModalContribution = ({
  disabled,
  editingContextId,
}: TreeToolBarContributionComponentProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'project.edit' });

  const [modal, setModal] = useState<Modal | null>(null);

  const onFinished = () => {
    setModal(null);
  };

  let modalElement: JSX.Element | null = null;
  if (modal === 'UploadDocument') {
    modalElement = <UploadDocumentModal editingContextId={editingContextId} onClose={onFinished} />;
  }

  return (
    <Fragment key="upload-document-modal-contribution">
      <IconButton
        disabled={disabled}
        size="small"
        color="inherit"
        aria-label={t('uploadModel')}
        title={t('uploadModel')}
        onClick={() => setModal('UploadDocument')}
        data-testid="upload-document-icon">
        <PublishIcon />
      </IconButton>
      {modalElement}
    </Fragment>
  );
};
