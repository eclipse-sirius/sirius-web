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
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@mui/material/IconButton';
import { Fragment, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { NewDocumentModal } from '../../../modals/new-document/NewDocumentModal';

type Modal = 'NewDocument';

export const NewDocumentModalContribution = ({ disabled, editingContextId }: TreeToolBarContributionComponentProps) => {
  const { t } = useTranslation('siriusWebApplication', { keyPrefix: 'project.edit' });

  const [modal, setModal] = useState<Modal | null>(null);

  let modalElement: JSX.Element | null = null;
  if (modal === 'NewDocument') {
    modalElement = <NewDocumentModal editingContextId={editingContextId} onClose={() => setModal(null)} />;
  }

  return (
    <Fragment key="new-document-modal-contribution">
      <IconButton
        disabled={disabled}
        size="small"
        color="inherit"
        aria-label={t('newModel')}
        title={t('newModel')}
        onClick={() => setModal('NewDocument')}
        data-testid="new-model">
        <AddIcon />
      </IconButton>
      {modalElement}
    </Fragment>
  );
};
