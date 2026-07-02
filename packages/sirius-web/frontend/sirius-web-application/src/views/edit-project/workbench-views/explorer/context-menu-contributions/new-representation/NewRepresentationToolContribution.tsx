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
import { NewRepresentationModal } from './NewRepresentationModal';
import { NewRepresentationToolContributionState } from './NewRepresentationToolContribution.types';

export const NewRepresentationToolContribution = forwardRef(
  ({ onInvoked }: PaletteToolOverriddenContributionComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const [state, setState] = useState<NewRepresentationToolContributionState>({
      isModalOpen: false,
    });
    const { editingContextId, item, readOnly, onClose, selectTreeItems, expandItem } =
      useContext<TreePaletteContextValue>(TreePaletteContext);

    const { t } = useTranslation('sirius-web-application', {
      keyPrefix: 'newRepresentationTreeItemContextMenuContribution',
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
          key="new-representation"
          onClick={handleClick}
          ref={ref}
          data-testid="new-representation"
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('newRepresentation')} />
        </MenuItem>
        {state.isModalOpen ? (
          <NewRepresentationModal
            editingContextId={editingContextId}
            item={item}
            onRepresentationCreated={onObjectCreated}
            onClose={onClose}
          />
        ) : null}
      </>
    );
  }
);
