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

import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import ScienceOutlinedIcon from '@mui/icons-material/ScienceOutlined';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { ReactElement, useState } from 'react';

export const DiagramCoverageProbe = (): ReactElement => {
  const [activated, setActivated] = useState<boolean>(false);

  const toggleProbe = (): void => {
    setActivated((previousValue) => !previousValue);
  };

  const label: string = activated ? 'Deactivate coverage probe' : 'Activate coverage probe';

  return (
    <Tooltip title={label}>
      <IconButton size="small" aria-label={label} onClick={toggleProbe} data-testid="diagram-coverage-probe">
        {activated ? <CheckCircleOutlineIcon /> : <ScienceOutlinedIcon />}
      </IconButton>
    </Tooltip>
  );
};
