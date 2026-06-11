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
import { usePalette } from '@eclipse-sirius/sirius-components-palette';
import { useCurrentProject } from '@eclipse-sirius/sirius-web-application';
import RadarIcon from '@mui/icons-material/Radar';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { Fragment, useState } from 'react';
import { makeStyles } from 'tss-react/mui';

const useStyle = makeStyles()(() => ({
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
}));

type Modal = 'dialog';
export const PapayaComponentDiagramToolContribution = () => {
  const { project } = useCurrentProject();
  const [modal, setModal] = useState<Modal | null>(null);
  const { classes } = useStyle();
  const { x, y } = usePalette();

  const onClose = () => {
    setModal(null);
  };

  let modalElement: JSX.Element | null = null;
  if (modal === 'dialog') {
    modalElement = (
      <>
        <Dialog open={true} onClose={onClose} fullWidth>
          <DialogContent>
            <DialogContentText>
              x: {x}, y:{y}
            </DialogContentText>
          </DialogContent>
        </Dialog>
      </>
    );
  }

  if (project.natures.filter((nature) => nature.name === 'siriusComponents://nature?kind=papaya').length > 0) {
    return (
      <Fragment key="coordinates-modal-contribution">
        <Tooltip key={'tooltip_'} title={'coordinates'} placement="right">
          <ListItemButton
            onClick={() => setModal('dialog')}
            data-testid="coordinates-tool"
            className={classes.listItemButton}>
            <RadarIcon sx={{ fontSize: 16, marginRight: 2 }} />
            <ListItemText primary={'Coordinates'} className={classes.listItemText} />
          </ListItemButton>
        </Tooltip>
        {modalElement}
      </Fragment>
    );
  }
  return null;
};
