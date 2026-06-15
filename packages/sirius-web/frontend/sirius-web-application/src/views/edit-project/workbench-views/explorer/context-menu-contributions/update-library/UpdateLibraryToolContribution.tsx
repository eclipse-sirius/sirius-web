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
  isSingleClickOnDiagramElementTool,
  PaletteToolOverriddenContributionComponentProps,
} from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import RefreshIcon from '@mui/icons-material/Refresh';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, Fragment, useContext, useState } from 'react';
import { useObject } from '../useObject';
import { UpdateLibraryModal } from './UpdateLibraryModal';

export const UpdateLibraryToolContribution = forwardRef(
  ({ tool }: PaletteToolOverriddenContributionComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const [dialogOpened, setDialogOpened] = useState<boolean>(false);
    const { editingContextId, item, readOnly, onClose } = useContext<TreePaletteContextValue>(TreePaletteContext);
    let fragment = null;

    const { data } = useObject(editingContextId, item.id);
    if (data) {
      const library = data.viewer?.editingContext?.object?.library;
      if (library) {
        fragment = (
          <Fragment key="update-library-tree-item-context-menu-contribution">
            <MenuItem
              key="update-library"
              onClick={() => setDialogOpened(true)}
              data-testid="update-library"
              disabled={readOnly}
              ref={ref}
              aria-disabled>
              <ListItemIcon>
                <RefreshIcon fontSize="small" />
              </ListItemIcon>
              <ListItemText primary="Update Library" />
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

    return fragment;
  }
);
