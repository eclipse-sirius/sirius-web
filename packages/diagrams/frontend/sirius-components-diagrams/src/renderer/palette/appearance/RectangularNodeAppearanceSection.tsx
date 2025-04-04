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
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import ListItem from '@mui/material/ListItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../contexts/DiagramContext.types';
import { GQLRectangularNodeStyle } from '../../../graphql/subscription/nodeFragment.types';
import { InsideLabel } from '../../DiagramRenderer.types';
import { RectangularNodeAppearanceSectionProps } from './RectangularNodeAppearanceSection.types';
import { useResetLabelAppearance } from './useResetLabelAppearance';
import { useResetNodeAppearance } from './useResetNodeAppearance';
import { useUpdateRectangularNodeAppearance } from './useUpdateRectangularNodeAppearance';

const useStyle = makeStyles()((theme) => ({
  textfield: {
    paddingLeft: theme.spacing(2),
  },
}));

export const RectangularNodeAppearanceSection = ({ nodeId, nodeData }: RectangularNodeAppearanceSectionProps) => {
  const { classes } = useStyle();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { updateBackground, updateInsideLabelBold } = useUpdateRectangularNodeAppearance(
    editingContextId,
    diagramId,
    nodeId
  );

  const { resetBackground } = useResetNodeAppearance(editingContextId, diagramId, nodeId);

  const { resetInsideLabelBold } = useResetLabelAppearance(editingContextId, diagramId, nodeId);

  const effectiveNodeStyle = nodeData.nodeAppearanceData.gqlStyle as GQLRectangularNodeStyle;
  const nodeCustomizedStyleProperties = nodeData.nodeAppearanceData.customizedStyleProperties;

  const effectiveNodeFillColor = effectiveNodeStyle.background;
  const isNodeFillColorCustomized = nodeCustomizedStyleProperties.some((property) => property === 'BACKGROUND');

  const hasInsideLabel = !!nodeData.insideLabel;
  const effectiveInsideLabelStyle = hasInsideLabel
    ? (nodeData.insideLabel as InsideLabel).appearanceData.gqlStyle
    : null;
  const labelCustomizedStyleProperties = hasInsideLabel
    ? (nodeData.insideLabel as InsideLabel).appearanceData.customizedStyleProperties
    : [];

  const isLabelBold = hasInsideLabel ? !!effectiveInsideLabelStyle?.bold : false;
  const isLabelBoldCustomized = hasInsideLabel
    ? labelCustomizedStyleProperties.some((property) => property === 'BOLD')
    : false;

  const [localNodeFillColor, setLocalNodeFillColor] = useState<string>(effectiveNodeFillColor);

  return (
    <>
      <ListItem>
        <Typography variant="subtitle2">Background</Typography>
        <TextField
          className={classes.textfield}
          variant="standard"
          value={localNodeFillColor}
          onChange={(event) => setLocalNodeFillColor(event.target.value)}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateBackground(localNodeFillColor);
            }
          }}
          onBlur={() => updateBackground(localNodeFillColor)}
        />
        {isNodeFillColorCustomized ? (
          <IconButton
            aria-label="reset"
            onClick={() => {
              resetBackground();
            }}>
            <FormatColorResetIcon />
          </IconButton>
        ) : null}
      </ListItem>
      {hasInsideLabel ? (
        <ListItem>
          <Typography variant="subtitle2">Label bold</Typography>
          <Checkbox
            checked={isLabelBold}
            onChange={(_, checked) => {
              updateInsideLabelBold((nodeData.insideLabel as InsideLabel).id, checked);
            }}
          />
          {isLabelBoldCustomized ? (
            <IconButton
              aria-label="reset"
              onClick={() => {
                resetInsideLabelBold((nodeData.insideLabel as InsideLabel).id);
              }}>
              <FormatColorResetIcon />
            </IconButton>
          ) : null}
        </ListItem>
      ) : null}
    </>
  );
};
