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
import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import CircularProgress from '@mui/material/CircularProgress';
import IconButton from '@mui/material/IconButton';
import ListItem from '@mui/material/ListItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../contexts/DiagramContext.types';
import { NodeTypeAppearanceSectionProps } from '../../../contexts/NodeContext.types';
import { GQLRectangularNodeStyle } from '../../../graphql/subscription/nodeFragment.types';
import { InsideLabel } from '../../DiagramRenderer.types';
import { useUpdateRectangularNodeAppearance } from './useUpdateRectangularNodeAppearance';

const useStyle = makeStyles()(() => ({
  appearanceSectionContainer: {},
  textfield: {
    paddingLeft: '10px',
  },
  loadingIndicatorContainer: {
    display: 'flex',
    justifyContent: 'center',
  },
}));

export const RectangularNodeAppearanceSection = ({ nodeId, nodeData }: NodeTypeAppearanceSectionProps) => {
  const { classes } = useStyle();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [waitingForRefresh, setWaitingForRefresh] = useState<boolean>(false);

  const { updateBackground, updateInsideLabelBold, resetBackground, resetInsideLabelBold } =
    useUpdateRectangularNodeAppearance(editingContextId, diagramId, nodeId);

  const effectiveNodeStyle = nodeData.nodeAppearanceData.effectiveNodeStyle as GQLRectangularNodeStyle;

  const effectiveNodeFillColor = effectiveNodeStyle.background;
  const hasInsideLabel = !!nodeData.insideLabel;
  const isLabelBold = hasInsideLabel
    ? (nodeData.insideLabel as InsideLabel).appearanceData.effectiveLabelStyle.bold
    : false;

  const customizedNodeStyle = nodeData.nodeAppearanceData.customizedNodeStyle as GQLRectangularNodeStyle;
  const isNodeFillColorCustomized = !!customizedNodeStyle ? !!customizedNodeStyle.background : false;
  const customizedInsideLabelStyle = nodeData.insideLabel?.appearanceData?.customizedLabelStyle;
  const isLabelBoldCustomized = !!customizedInsideLabelStyle ? customizedInsideLabelStyle.bold != null : false;

  const [localNodeFillColor, setLocalNodeFillColor] = useState<string>(effectiveNodeFillColor);

  if (waitingForRefresh) {
    return (
      <Box className={classes.loadingIndicatorContainer}>
        <CircularProgress />
      </Box>
    );
  } else {
    return (
      <Box className={classes.appearanceSectionContainer}>
        <ListItem>
          <Typography variant="subtitle2">Fill</Typography>
          <TextField
            className={classes.textfield}
            variant="standard"
            value={localNodeFillColor}
            onChange={(e) => setLocalNodeFillColor(e.target.value)}
            onKeyDown={(e) => {
              if (e.code === 'Enter') {
                updateBackground(localNodeFillColor);
                setWaitingForRefresh(true);
              }
            }}
            onBlur={() => setLocalNodeFillColor(effectiveNodeFillColor)}
          />
          {isNodeFillColorCustomized ? (
            <IconButton
              aria-label="reset"
              onClick={() => {
                resetBackground(effectiveNodeFillColor);
                setWaitingForRefresh(true);
              }}>
              <FormatColorResetIcon />
            </IconButton>
          ) : (
            <></>
          )}
        </ListItem>
        {hasInsideLabel ? (
          <ListItem>
            <Typography variant="subtitle2">Label bold</Typography>
            <Checkbox
              checked={isLabelBold}
              onChange={(_, checked) => {
                updateInsideLabelBold((nodeData.insideLabel as InsideLabel).id, checked);
                setWaitingForRefresh(true);
              }}
            />
            {isLabelBoldCustomized ? (
              <IconButton
                aria-label="reset"
                onClick={() => {
                  resetInsideLabelBold((nodeData.insideLabel as InsideLabel).id, isLabelBold);
                  setWaitingForRefresh(true);
                }}>
                <FormatColorResetIcon />
              </IconButton>
            ) : (
              <></>
            )}
          </ListItem>
        ) : (
          <></>
        )}
      </Box>
    );
  }
};
