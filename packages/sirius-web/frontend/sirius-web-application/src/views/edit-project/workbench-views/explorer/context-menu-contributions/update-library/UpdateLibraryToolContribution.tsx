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
import {
  fuzzyMatch,
  isSingleClickOnDiagramElementTool,
  PaletteToolOverriddenContributionComponentProps,
  ToolListItemText,
} from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import RefreshIcon from '@mui/icons-material/Refresh';
import ListItemIcon from '@mui/material/ListItemIcon';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, Fragment, useContext, useState } from 'react';
import { useObject } from '../useObject';
import { UpdateLibraryModal } from './UpdateLibraryModal';

export const UpdateLibraryToolContribution = forwardRef(
  (
    { onInvoked, searchedValue, tool }: PaletteToolOverriddenContributionComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [dialogOpened, setDialogOpened] = useState<boolean>(false);
    const { editingContextId, item, readOnly, onClose } = useContext<TreePaletteContextValue>(TreePaletteContext);

    let fragment = null;

    const handleClick = () => {
      onInvoked();
      setDialogOpened(true);
    };

    const { data } = useObject(editingContextId, item.id);
    if (data) {
      const library = data.viewer?.editingContext?.object?.library;
      if (library) {
        fragment = (
          <Fragment key="update-library-tree-item-context-menu-contribution">
            <MenuItem
              key="update-library"
              onClick={handleClick}
              data-testid="update-library"
              disabled={readOnly}
              ref={ref}>
              <ListItemIcon>
                <RefreshIcon fontSize="small" />
              </ListItemIcon>
              <ToolListItemText label={tool.label} searchedValue={searchedValue} />
            </MenuItem>
            <UpdateLibraryModal
              open={dialogOpened}
              namespace={library.namespace}
              name={library.name}
              version={library.version}
              title={`Update ${library.name} @${library.version}`}
              withImpactAnalysis={isSingleClickOnDiagramElementTool(tool) && tool.withImpactAnalysis}
              onClose={onClose}
            />
          </Fragment>
        );
      }
    }

    const matchResult = searchedValue ? fuzzyMatch(tool.label, searchedValue) : null;
    if (!!searchedValue && !matchResult?.matches) {
      return null;
    }

    return fragment;
  }
);
