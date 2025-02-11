/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useCurrentProject } from '@eclipse-sirius/sirius-web-application';
import RadarIcon from '@mui/icons-material/Radar';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import IconButton from '@mui/material/IconButton';
import { Theme } from '@mui/material/styles';
import { Fragment, useState } from 'react';
import { makeStyles } from 'tss-react/mui';

const useToolStyle = makeStyles()((theme: Theme) => ({
  tool: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

type Modal = 'dialog';
export const PapayaComponentDiagramToolContribution = ({ x, y }) => {
  const { project } = useCurrentProject();
  const [modal, setModal] = useState<Modal | null>(null);
  const { classes } = useToolStyle();

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
        <IconButton
          className={classes.tool}
          size="small"
          color="inherit"
          aria-label="Coordinates"
          title="Coordinates"
          onClick={() => setModal('dialog')}
          data-testid="coordinates-tool">
          <RadarIcon sx={{ fontSize: 16 }} />
        </IconButton>
        {modalElement}
      </Fragment>
    );
  }
  return null;
};
