/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
  DiagramContext,
  DiagramContextValue,
  DiagramToolbarActionProps,
} from '@eclipse-sirius/sirius-components-diagrams';
import FilterListIcon from '@mui/icons-material/FilterList';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import Tooltip from '@mui/material/Tooltip';
import { useContext, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramFilterForm } from './DiagramFilterForm';

export const DiagramFilter = ({ editingContextId, diagramId }: DiagramToolbarActionProps) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const handlePanel = () => setIsOpen(() => true);

  const anchorRef = useRef<HTMLButtonElement | null>(null);
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'diagramFilter' });

  return (
    <>
      <Tooltip title={t('filter')} ref={anchorRef}>
        <span>
          <IconButton
            size="small"
            aria-label={t('filter')}
            onClick={handlePanel}
            data-testid="filter-elements"
            disabled={readOnly}>
            <FilterListIcon />
          </IconButton>
        </span>
      </Tooltip>
      <Menu anchorEl={anchorRef.current} open={isOpen} onClose={() => setIsOpen(false)}>
        <DiagramFilterForm editingContextId={editingContextId} diagramId={diagramId} />
      </Menu>
    </>
  );
};
