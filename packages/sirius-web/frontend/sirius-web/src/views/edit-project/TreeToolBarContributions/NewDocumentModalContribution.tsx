/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import IconButton from '@material-ui/core/IconButton';
import { Add as AddIcon } from '@material-ui/icons';
import { Fragment, useState } from 'react';
import { NewDocumentModal } from '../../../modals/new-document/NewDocumentModal';

type Modal = 'NewDocument';

export const NewDocumentModalContribution = ({ disabled, editingContextId }: TreeToolBarContributionComponentProps) => {
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
        aria-label="New model"
        title="New model"
        onClick={() => setModal('NewDocument')}
        data-testid="new-model">
        <AddIcon />
      </IconButton>
      {modalElement}
    </Fragment>
  );
};
