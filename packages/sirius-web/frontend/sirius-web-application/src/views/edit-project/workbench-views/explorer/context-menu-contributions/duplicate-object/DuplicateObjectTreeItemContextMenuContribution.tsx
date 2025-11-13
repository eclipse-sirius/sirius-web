/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import AddToPhotosIcon from '@mui/icons-material/AddToPhotos';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { DuplicateObjectModal } from './DuplicateObjectModal';
import { DuplicateObjectTreeItemContextMenuContributionState } from './DuplicateObjectTreeItemContextMenuContribution.types';

export const DuplicateObjectTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, selectTreeItems, expandItem, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [state, setState] = useState<DuplicateObjectTreeItemContextMenuContributionState>({
      isModalOpen: false,
    });

    const { t } = useTranslation('sirius-web-application', {
      keyPrefix: 'duplicateObjectTreeItemContextMenuContribution',
    });

    const { setSelection } = useSelection();
    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      selectTreeItems(selection.entries.map((entry) => entry.id));
      expandItem();
      onClose();
    };

    return (
      <>
        <MenuItem
          key="duplicate-object"
          onClick={() => setState((prevState) => ({ ...prevState, isModalOpen: true }))}
          ref={ref}
          data-testid="duplicate-object"
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddToPhotosIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('duplicate')} />
        </MenuItem>
        {state.isModalOpen ? (
          <DuplicateObjectModal
            editingContextId={editingContextId}
            objectToDuplicateId={item.id}
            objectToDuplicateKind={item.kind}
            onObjectDuplicated={onObjectCreated}
            onClose={onClose}
          />
        ) : null}
      </>
    );
  }
);
