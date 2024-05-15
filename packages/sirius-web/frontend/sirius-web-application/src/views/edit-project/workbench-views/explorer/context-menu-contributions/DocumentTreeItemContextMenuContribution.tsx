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
import { Selection, ServerContext, ServerContextValue, useSelection } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import AddIcon from '@mui/icons-material/Add';
import GetAppIcon from '@mui/icons-material/GetApp';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Fragment, forwardRef, useContext, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { NewRootObjectModal } from '../../../../../modals/new-root-object/NewRootObjectModal';

type Modal = 'CreateNewRootObject';

export const DocumentTreeItemContextMenuContribution = forwardRef(
  (
    {
      editingContextId,
      treeId,
      item,
      readOnly,
      selectTreeItems,
      expandItem,
      onClose,
    }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'project.edit' });
    const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
    const [modal, setModal] = useState<Modal | null>(null);
    const { setSelection } = useSelection();

    if (!treeId.startsWith('explorer://') || !item.kind.startsWith('siriusWeb://document')) {
      return null;
    }

    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      selectTreeItems(selection.entries.map((entry) => entry.id));
      expandItem();
      onClose();
    };

    let modalElement = null;
    if (modal === 'CreateNewRootObject') {
      modalElement = (
        <NewRootObjectModal
          editingContextId={editingContextId}
          item={item}
          onObjectCreated={onObjectCreated}
          onClose={onClose}
        />
      );
    }

    return (
      <Fragment key="document-tree-item-context-menu-contribution">
        <MenuItem
          key="new-object"
          data-testid="new-object"
          onClick={() => setModal('CreateNewRootObject')}
          ref={ref}
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('newObject')} />
        </MenuItem>
        <MenuItem
          key="download"
          divider
          onClick={onClose}
          component="a"
          href={`${httpOrigin}/api/editingcontexts/${editingContextId}/documents/${item.id}`}
          type="application/octet-stream"
          data-testid="download"
          aria-disabled>
          <ListItemIcon>
            <GetAppIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('download')} aria-disabled />
        </MenuItem>
        {modalElement}
      </Fragment>
    );
  }
);
