/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { PaletteToolOverriddenContributionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import AddIcon from '@mui/icons-material/Add';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useContext, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { NewRootObjectModal } from './NewRootObjectModal';
import { NewRootObjectToolContributionState } from './NewRootObjectToolContribution.types';

export const NewRootObjectToolContribution = forwardRef(
  ({ onInvoked }: PaletteToolOverriddenContributionComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const [state, setState] = useState<NewRootObjectToolContributionState>({
      isModalOpen: false,
    });
    const { editingContextId, item, readOnly, selectTreeItems, expandItem, onClose } =
      useContext<TreePaletteContextValue>(TreePaletteContext);

    const { t } = useTranslation('sirius-web-application', {
      keyPrefix: 'newRootObjectTreeItemContextMenuContribution',
    });

    const { setSelection } = useSelection();
    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      selectTreeItems(selection.entries.map((entry) => entry.id));
      expandItem();
      onClose();
    };

    const handleClick = () => {
      onInvoked();
      setState((prevState) => ({ ...prevState, isModalOpen: true }));
    };

    return (
      <>
        <MenuItem
          key="new-object"
          data-testid="new-object"
          onClick={handleClick}
          ref={ref}
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('newObject')} />
        </MenuItem>
        {state.isModalOpen ? (
          <NewRootObjectModal
            editingContextId={editingContextId}
            item={item}
            onObjectCreated={onObjectCreated}
            onClose={onClose}
          />
        ) : null}
      </>
    );
  }
);
