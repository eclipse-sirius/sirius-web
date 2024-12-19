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
import { WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import PlayCircleOutlineIcon from '@mui/icons-material/PlayCircleOutline';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import { SxProps, Theme } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { ComponentType, useState } from 'react';
import {
  ExpressionAreaProps,
  ExpressionAreaState,
  ExpressionResultViewerProps,
  ResultAreaProps,
} from './QueryView.types';
import { useEvaluateExpression } from './useEvaluateExpression';
import {
  GQLBooleanExpressionResult,
  GQLIntExpressionResult,
  GQLObjectExpressionResult,
  GQLObjectsExpressionResult,
  GQLStringExpressionResult,
} from './useEvaluateExpression.types';

export const QueryView = ({ editingContextId, readOnly }: WorkbenchViewComponentProps) => {
  const interpreterStyle: SxProps<Theme> = (theme) => ({
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(2),
    paddingX: theme.spacing(1),
  });

  const { evaluateExpression, loading, result } = useEvaluateExpression();

  const handleEvaluateExpression = (expression: string) => evaluateExpression(editingContextId, expression);

  return (
    <Box data-representation-kind="interpreter" sx={interpreterStyle}>
      <ExpressionArea onEvaluateExpression={handleEvaluateExpression} disabled={loading || readOnly} />
      <ResultArea payload={result} />
    </Box>
  );
};

const ExpressionArea = ({ onEvaluateExpression, disabled }: ExpressionAreaProps) => {
  const [state, setState] = useState<ExpressionAreaState>({
    expression: '',
  });

  const handleExpressionChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const {
      target: { value },
    } = event;
    setState((prevState) => ({ ...prevState, expression: value }));
  };

  const handleKeyDown: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
    if ('Enter' === event.key && event.altKey) {
      onEvaluateExpression(state.expression);
    }
  };

  const expressionAreaToolbarStyle: SxProps<Theme> = {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  };

  return (
    <div>
      <Box sx={expressionAreaToolbarStyle}>
        <Typography variant="subtitle2">Expression</Typography>

        <IconButton
          onClick={() => onEvaluateExpression(state.expression)}
          color={state.expression.trim().length > 0 ? 'primary' : undefined}
          disabled={disabled || state.expression.trim().length === 0}>
          <PlayCircleOutlineIcon />
        </IconButton>
      </Box>
      <div>
        <TextField
          value={state.expression}
          onChange={handleExpressionChange}
          onKeyDown={handleKeyDown}
          disabled={disabled}
          multiline
          minRows={5}
          fullWidth
          sx={{
            backgroundColor: '#fff8e5',
          }}
        />
      </div>
    </div>
  );
};

const ObjectExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'ObjectExpressionResult') {
    return null;
  }

  const { objectValue } = result as GQLObjectExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        One object returned
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={objectValue.label} />
        </ListItem>
      </List>
    </Box>
  );
};

const ObjectsExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'ObjectsExpressionResult') {
    return null;
  }

  const { objectsValue } = result as GQLObjectsExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        {objectsValue.length} object(s) returned
      </Typography>
      <List dense>
        {objectsValue.map((object) => {
          return (
            <ListItem key={object.id}>
              <ListItemText primary={object.label} />
            </ListItem>
          );
        })}
      </List>
    </Box>
  );
};

const BooleanExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'BooleanExpressionResult') {
    return null;
  }

  const { booleanValue } = result as GQLBooleanExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        One boolean returned
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={booleanValue} />
        </ListItem>
      </List>
    </Box>
  );
};

const StringExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'StringExpressionResult') {
    return null;
  }

  const { stringValue } = result as GQLStringExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        One string returned
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={stringValue} />
        </ListItem>
      </List>
    </Box>
  );
};

const IntExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'IntExpressionResult') {
    return null;
  }

  const { intValue } = result as GQLIntExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        One integer returned
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={intValue} />
        </ListItem>
      </List>
    </Box>
  );
};

const resultType2viewer: Record<string, ComponentType<ExpressionResultViewerProps>> = {
  ObjectExpressionResult: ObjectExpressionResultViewer,
  ObjectsExpressionResult: ObjectsExpressionResultViewer,
  BooleanExpressionResult: BooleanExpressionResultViewer,
  StringExpressionResult: StringExpressionResultViewer,
  IntExpressionResult: IntExpressionResultViewer,
};

const ResultArea = ({ payload }: ResultAreaProps) => {
  const resultAreaToolbarStyle: SxProps<Theme> = {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  };

  const titleAreaStyle: SxProps<Theme> = {
    display: 'flex',
    flexDirection: 'column',
  };

  let content: JSX.Element | null = null;
  if (payload) {
    const Viewer = resultType2viewer[payload.result.__typename];
    if (Viewer) {
      content = <Viewer result={payload.result} />;
    }
  }

  return (
    <div>
      <Box sx={resultAreaToolbarStyle}>
        <Box sx={titleAreaStyle}>
          <Typography variant="subtitle2">Evaluation result</Typography>
        </Box>
      </Box>

      {content}
    </div>
  );
};
